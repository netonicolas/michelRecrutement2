import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompetenceMySuffix } from './competence-my-suffix.model';
import { CompetenceMySuffixPopupService } from './competence-my-suffix-popup.service';
import { CompetenceMySuffixService } from './competence-my-suffix.service';
import { OffreMySuffix, OffreMySuffixService } from '../offre-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-competence-my-suffix-dialog',
    templateUrl: './competence-my-suffix-dialog.component.html'
})
export class CompetenceMySuffixDialogComponent implements OnInit {

    competence: CompetenceMySuffix;
    isSaving: boolean;

    offres: OffreMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private competenceService: CompetenceMySuffixService,
        private offreService: OffreMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.offreService.query()
            .subscribe((res: ResponseWrapper) => { this.offres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.competence.id !== undefined) {
            this.subscribeToSaveResponse(
                this.competenceService.update(this.competence));
        } else {
            this.subscribeToSaveResponse(
                this.competenceService.create(this.competence));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompetenceMySuffix>) {
        result.subscribe((res: CompetenceMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompetenceMySuffix) {
        this.eventManager.broadcast({ name: 'competenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOffreById(index: number, item: OffreMySuffix) {
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
    selector: 'jhi-competence-my-suffix-popup',
    template: ''
})
export class CompetenceMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private competencePopupService: CompetenceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.competencePopupService
                    .open(CompetenceMySuffixDialogComponent as Component, params['id']);
            } else {
                this.competencePopupService
                    .open(CompetenceMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
