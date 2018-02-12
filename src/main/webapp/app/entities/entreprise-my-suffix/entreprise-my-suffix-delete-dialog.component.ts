import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntrepriseMySuffix } from './entreprise-my-suffix.model';
import { EntrepriseMySuffixPopupService } from './entreprise-my-suffix-popup.service';
import { EntrepriseMySuffixService } from './entreprise-my-suffix.service';

@Component({
    selector: 'jhi-entreprise-my-suffix-delete-dialog',
    templateUrl: './entreprise-my-suffix-delete-dialog.component.html'
})
export class EntrepriseMySuffixDeleteDialogComponent {

    entreprise: EntrepriseMySuffix;

    constructor(
        private entrepriseService: EntrepriseMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entrepriseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entrepriseListModification',
                content: 'Deleted an entreprise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entreprise-my-suffix-delete-popup',
    template: ''
})
export class EntrepriseMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreprisePopupService: EntrepriseMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entreprisePopupService
                .open(EntrepriseMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
