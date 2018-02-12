import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompetenceMySuffixComponent } from './competence-my-suffix.component';
import { CompetenceMySuffixDetailComponent } from './competence-my-suffix-detail.component';
import { CompetenceMySuffixPopupComponent } from './competence-my-suffix-dialog.component';
import { CompetenceMySuffixDeletePopupComponent } from './competence-my-suffix-delete-dialog.component';

export const competenceRoute: Routes = [
    {
        path: 'competence-my-suffix',
        component: CompetenceMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.competence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'competence-my-suffix/:id',
        component: CompetenceMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.competence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const competencePopupRoute: Routes = [
    {
        path: 'competence-my-suffix-new',
        component: CompetenceMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.competence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'competence-my-suffix/:id/edit',
        component: CompetenceMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.competence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'competence-my-suffix/:id/delete',
        component: CompetenceMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.competence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
