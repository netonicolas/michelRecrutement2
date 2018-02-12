import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntrepriseMySuffix } from './entreprise-my-suffix.model';
import { EntrepriseMySuffixPopupService } from './entreprise-my-suffix-popup.service';
import { EntrepriseMySuffixService } from './entreprise-my-suffix.service';
import { LieuMySuffix, LieuMySuffixService } from '../lieu-my-suffix';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-entreprise-my-suffix-dialog',
    templateUrl: './entreprise-my-suffix-dialog.component.html'
})
export class EntrepriseMySuffixDialogComponent implements OnInit {

    entreprise: EntrepriseMySuffix;
    isSaving: boolean;

    lieuentreprises: LieuMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private entrepriseService: EntrepriseMySuffixService,
        private lieuService: LieuMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.lieuService
            .query({filter: 'entreprise-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.entreprise.lieuEntrepriseId) {
                    this.lieuentreprises = res.json;
                } else {
                    this.lieuService
                        .find(this.entreprise.lieuEntrepriseId)
                        .subscribe((subRes: LieuMySuffix) => {
                            this.lieuentreprises = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entreprise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entrepriseService.update(this.entreprise));
        } else {
            this.subscribeToSaveResponse(
                this.entrepriseService.create(this.entreprise));
        }
    }

    private subscribeToSaveResponse(result: Observable<EntrepriseMySuffix>) {
        result.subscribe((res: EntrepriseMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EntrepriseMySuffix) {
        this.eventManager.broadcast({ name: 'entrepriseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLieuById(index: number, item: LieuMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entreprise-my-suffix-popup',
    template: ''
})
export class EntrepriseMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreprisePopupService: EntrepriseMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entreprisePopupService
                    .open(EntrepriseMySuffixDialogComponent as Component, params['id']);
            } else {
                this.entreprisePopupService
                    .open(EntrepriseMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
