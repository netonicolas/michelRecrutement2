import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { OffreMySuffixComponent } from './offre-my-suffix.component';
import { OffreMySuffixDetailComponent } from './offre-my-suffix-detail.component';
import { OffreMySuffixPopupComponent } from './offre-my-suffix-dialog.component';
import { OffreMySuffixDeletePopupComponent } from './offre-my-suffix-delete-dialog.component';

export const offreRoute: Routes = [
    {
        path: 'offre-my-suffix',
        component: OffreMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.offre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'offre-my-suffix/:id',
        component: OffreMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.offre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const offrePopupRoute: Routes = [
    {
        path: 'offre-my-suffix-new',
        component: OffreMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.offre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'offre-my-suffix/:id/edit',
        component: OffreMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.offre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'offre-my-suffix/:id/delete',
        component: OffreMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.offre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
