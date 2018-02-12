import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OffreMySuffix } from './offre-my-suffix.model';
import { OffreMySuffixService } from './offre-my-suffix.service';

@Component({
    selector: 'jhi-offre-my-suffix-detail',
    templateUrl: './offre-my-suffix-detail.component.html'
})
export class OffreMySuffixDetailComponent implements OnInit, OnDestroy {

    offre: OffreMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private offreService: OffreMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOffres();
    }

    load(id) {
        this.offreService.find(id).subscribe((offre) => {
            this.offre = offre;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOffres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'offreListModification',
            (response) => this.load(this.offre.id)
        );
    }
}
