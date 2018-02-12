import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LieuMySuffix } from './lieu-my-suffix.model';
import { LieuMySuffixPopupService } from './lieu-my-suffix-popup.service';
import { LieuMySuffixService } from './lieu-my-suffix.service';

@Component({
    selector: 'jhi-lieu-my-suffix-dialog',
    templateUrl: './lieu-my-suffix-dialog.component.html'
})
export class LieuMySuffixDialogComponent implements OnInit {

    lieu: LieuMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private lieuService: LieuMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lieu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lieuService.update(this.lieu));
        } else {
            this.subscribeToSaveResponse(
                this.lieuService.create(this.lieu));
        }
    }

    private subscribeToSaveResponse(result: Observable<LieuMySuffix>) {
        result.subscribe((res: LieuMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LieuMySuffix) {
        this.eventManager.broadcast({ name: 'lieuListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-lieu-my-suffix-popup',
    template: ''
})
export class LieuMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lieuPopupService: LieuMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lieuPopupService
                    .open(LieuMySuffixDialogComponent as Component, params['id']);
            } else {
                this.lieuPopupService
                    .open(LieuMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
