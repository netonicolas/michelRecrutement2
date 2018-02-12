import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompetenceMySuffix } from './competence-my-suffix.model';
import { CompetenceMySuffixPopupService } from './competence-my-suffix-popup.service';
import { CompetenceMySuffixService } from './competence-my-suffix.service';

@Component({
    selector: 'jhi-competence-my-suffix-delete-dialog',
    templateUrl: './competence-my-suffix-delete-dialog.component.html'
})
export class CompetenceMySuffixDeleteDialogComponent {

    competence: CompetenceMySuffix;

    constructor(
        private competenceService: CompetenceMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.competenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'competenceListModification',
                content: 'Deleted an competence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-competence-my-suffix-delete-popup',
    template: ''
})
export class CompetenceMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private competencePopupService: CompetenceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.competencePopupService
                .open(CompetenceMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
