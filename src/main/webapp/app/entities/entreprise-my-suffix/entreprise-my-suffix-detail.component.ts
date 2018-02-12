import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EntrepriseMySuffix } from './entreprise-my-suffix.model';
import { EntrepriseMySuffixService } from './entreprise-my-suffix.service';

@Component({
    selector: 'jhi-entreprise-my-suffix-detail',
    templateUrl: './entreprise-my-suffix-detail.component.html'
})
export class EntrepriseMySuffixDetailComponent implements OnInit, OnDestroy {

    entreprise: EntrepriseMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entrepriseService: EntrepriseMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntreprises();
    }

    load(id) {
        this.entrepriseService.find(id).subscribe((entreprise) => {
            this.entreprise = entreprise;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntreprises() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entrepriseListModification',
            (response) => this.load(this.entreprise.id)
        );
    }
}
