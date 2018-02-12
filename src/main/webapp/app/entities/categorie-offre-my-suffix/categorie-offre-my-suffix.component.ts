import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CategorieOffreMySuffix } from './categorie-offre-my-suffix.model';
import { CategorieOffreMySuffixService } from './categorie-offre-my-suffix.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-categorie-offre-my-suffix',
    templateUrl: './categorie-offre-my-suffix.component.html'
})
export class CategorieOffreMySuffixComponent implements OnInit, OnDestroy {
categorieOffres: CategorieOffreMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private categorieOffreService: CategorieOffreMySuffixService,
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
            this.categorieOffreService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.categorieOffres = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.categorieOffreService.query().subscribe(
            (res: ResponseWrapper) => {
                this.categorieOffres = res.json;
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
        this.registerChangeInCategorieOffres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CategorieOffreMySuffix) {
        return item.id;
    }
    registerChangeInCategorieOffres() {
        this.eventSubscriber = this.eventManager.subscribe('categorieOffreListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
