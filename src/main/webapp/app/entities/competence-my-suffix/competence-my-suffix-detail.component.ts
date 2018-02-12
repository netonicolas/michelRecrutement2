import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CompetenceMySuffix } from './competence-my-suffix.model';
import { CompetenceMySuffixService } from './competence-my-suffix.service';

@Component({
    selector: 'jhi-competence-my-suffix-detail',
    templateUrl: './competence-my-suffix-detail.component.html'
})
export class CompetenceMySuffixDetailComponent implements OnInit, OnDestroy {

    competence: CompetenceMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private competenceService: CompetenceMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompetences();
    }

    load(id) {
        this.competenceService.find(id).subscribe((competence) => {
            this.competence = competence;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompetences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'competenceListModification',
            (response) => this.load(this.competence.id)
        );
    }
}
