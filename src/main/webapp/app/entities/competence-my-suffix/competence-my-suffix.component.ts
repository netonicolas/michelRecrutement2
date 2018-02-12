import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompetenceMySuffix } from './competence-my-suffix.model';
import { CompetenceMySuffixService } from './competence-my-suffix.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-competence-my-suffix',
    templateUrl: './competence-my-suffix.component.html'
})
export class CompetenceMySuffixComponent implements OnInit, OnDestroy {
competences: CompetenceMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private competenceService: CompetenceMySuffixService,
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
            this.competenceService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.competences = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.competenceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.competences = res.json;
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
        this.registerChangeInCompetences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompetenceMySuffix) {
        return item.id;
    }
    registerChangeInCompetences() {
        this.eventSubscriber = this.eventManager.subscribe('competenceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
