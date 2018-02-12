import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LieuMySuffixComponent } from './lieu-my-suffix.component';
import { LieuMySuffixDetailComponent } from './lieu-my-suffix-detail.component';
import { LieuMySuffixPopupComponent } from './lieu-my-suffix-dialog.component';
import { LieuMySuffixDeletePopupComponent } from './lieu-my-suffix-delete-dialog.component';

export const lieuRoute: Routes = [
    {
        path: 'lieu-my-suffix',
        component: LieuMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.lieu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lieu-my-suffix/:id',
        component: LieuMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.lieu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lieuPopupRoute: Routes = [
    {
        path: 'lieu-my-suffix-new',
        component: LieuMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.lieu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lieu-my-suffix/:id/edit',
        component: LieuMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.lieu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lieu-my-suffix/:id/delete',
        component: LieuMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.lieu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
