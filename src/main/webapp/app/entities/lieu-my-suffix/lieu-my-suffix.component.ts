import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LieuMySuffix } from './lieu-my-suffix.model';
import { LieuMySuffixService } from './lieu-my-suffix.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lieu-my-suffix',
    templateUrl: './lieu-my-suffix.component.html'
})
export class LieuMySuffixComponent implements OnInit, OnDestroy {
lieus: LieuMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private lieuService: LieuMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.lieuService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.lieus = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.lieuService.query().subscribe(
            (res: ResponseWrapper) => {
                this.lieus = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLieus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LieuMySuffix) {
        return item.id;
    }
    registerChangeInLieus() {
        this.eventSubscriber = this.eventManager.subscribe('lieuListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
