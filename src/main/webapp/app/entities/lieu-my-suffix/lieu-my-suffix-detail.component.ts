import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LieuMySuffix } from './lieu-my-suffix.model';
import { LieuMySuffixService } from './lieu-my-suffix.service';

@Component({
    selector: 'jhi-lieu-my-suffix-detail',
    templateUrl: './lieu-my-suffix-detail.component.html'
})
export class LieuMySuffixDetailComponent implements OnInit, OnDestroy {

    lieu: LieuMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lieuService: LieuMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLieus();
    }

    load(id) {
        this.lieuService.find(id).subscribe((lieu) => {
            this.lieu = lieu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLieus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lieuListModification',
            (response) => this.load(this.lieu.id)
        );
    }
}
