import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LieuMySuffix } from './lieu-my-suffix.model';
import { LieuMySuffixPopupService } from './lieu-my-suffix-popup.service';
import { LieuMySuffixService } from './lieu-my-suffix.service';

@Component({
    selector: 'jhi-lieu-my-suffix-delete-dialog',
    templateUrl: './lieu-my-suffix-delete-dialog.component.html'
})
export class LieuMySuffixDeleteDialogComponent {

    lieu: LieuMySuffix;

    constructor(
        private lieuService: LieuMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lieuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lieuListModification',
                content: 'Deleted an lieu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lieu-my-suffix-delete-popup',
    template: ''
})
export class LieuMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lieuPopupService: LieuMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lieuPopupService
                .open(LieuMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
