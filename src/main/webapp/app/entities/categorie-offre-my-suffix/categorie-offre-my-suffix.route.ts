import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CategorieOffreMySuffixComponent } from './categorie-offre-my-suffix.component';
import { CategorieOffreMySuffixDetailComponent } from './categorie-offre-my-suffix-detail.component';
import { CategorieOffreMySuffixPopupComponent } from './categorie-offre-my-suffix-dialog.component';
import { CategorieOffreMySuffixDeletePopupComponent } from './categorie-offre-my-suffix-delete-dialog.component';

export const categorieOffreRoute: Routes = [
    {
        path: 'categorie-offre-my-suffix',
        component: CategorieOffreMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.categorieOffre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'categorie-offre-my-suffix/:id',
        component: CategorieOffreMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.categorieOffre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categorieOffrePopupRoute: Routes = [
    {
        path: 'categorie-offre-my-suffix-new',
        component: CategorieOffreMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.categorieOffre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie-offre-my-suffix/:id/edit',
        component: CategorieOffreMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.categorieOffre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie-offre-my-suffix/:id/delete',
        component: CategorieOffreMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.categorieOffre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
