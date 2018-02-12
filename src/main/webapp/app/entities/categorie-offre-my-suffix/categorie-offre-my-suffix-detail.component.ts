import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CategorieOffreMySuffix } from './categorie-offre-my-suffix.model';
import { CategorieOffreMySuffixService } from './categorie-offre-my-suffix.service';

@Component({
    selector: 'jhi-categorie-offre-my-suffix-detail',
    templateUrl: './categorie-offre-my-suffix-detail.component.html'
})
export class CategorieOffreMySuffixDetailComponent implements OnInit, OnDestroy {

    categorieOffre: CategorieOffreMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private categorieOffreService: CategorieOffreMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategorieOffres();
    }

    load(id) {
        this.categorieOffreService.find(id).subscribe((categorieOffre) => {
            this.categorieOffre = categorieOffre;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCategorieOffres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'categorieOffreListModification',
            (response) => this.load(this.categorieOffre.id)
        );
    }
}
