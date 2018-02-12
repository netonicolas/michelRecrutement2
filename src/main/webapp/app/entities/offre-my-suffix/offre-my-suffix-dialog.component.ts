import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OffreMySuffix } from './offre-my-suffix.model';
import { OffreMySuffixPopupService } from './offre-my-suffix-popup.service';
import { OffreMySuffixService } from './offre-my-suffix.service';
import { EntrepriseMySuffix, EntrepriseMySuffixService } from '../entreprise-my-suffix';
import { CategorieOffreMySuffix, CategorieOffreMySuffixService } from '../categorie-offre-my-suffix';
import { CompetenceMySuffix, CompetenceMySuffixService } from '../competence-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-offre-my-suffix-dialog',
    templateUrl: './offre-my-suffix-dialog.component.html'
})
export class OffreMySuffixDialogComponent implements OnInit {

    offre: OffreMySuffix;
    isSaving: boolean;

    entreprises: EntrepriseMySuffix[];

    categorieoffres: CategorieOffreMySuffix[];

    competences: CompetenceMySuffix[];
    dateOffresDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private offreService: OffreMySuffixService,
        private entrepriseService: EntrepriseMySuffixService,
        private categorieOffreService: CategorieOffreMySuffixService,
        private competenceService: CompetenceMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.entrepriseService
            .query({filter: 'offre-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.offre.entrepriseId) {
                    this.entreprises = res.json;
                } else {
                    this.entrepriseService
                        .find(this.offre.entrepriseId)
                        .subscribe((subRes: EntrepriseMySuffix) => {
                            this.entreprises = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.categorieOffreService
            .query({filter: 'offre-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.offre.categorieOffreId) {
                    this.categorieoffres = res.json;
                } else {
                    this.categorieOffreService
                        .find(this.offre.categorieOffreId)
                        .subscribe((subRes: CategorieOffreMySuffix) => {
                            this.categorieoffres = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.competenceService.query()
            .subscribe((res: ResponseWrapper) => { this.competences = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.offre.id !== undefined) {
            this.subscribeToSaveResponse(
                this.offreService.update(this.offre));
        } else {
            this.subscribeToSaveResponse(
                this.offreService.create(this.offre));
        }
    }

    private subscribeToSaveResponse(result: Observable<OffreMySuffix>) {
        result.subscribe((res: OffreMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OffreMySuffix) {
        this.eventManager.broadcast({ name: 'offreListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEntrepriseById(index: number, item: EntrepriseMySuffix) {
        return item.id;
    }

    trackCategorieOffreById(index: number, item: CategorieOffreMySuffix) {
        return item.id;
    }

    trackCompetenceById(index: number, item: CompetenceMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-offre-my-suffix-popup',
    template: ''
})
export class OffreMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private offrePopupService: OffreMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.offrePopupService
                    .open(OffreMySuffixDialogComponent as Component, params['id']);
            } else {
                this.offrePopupService
                    .open(OffreMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
