import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EntrepriseMySuffixComponent } from './entreprise-my-suffix.component';
import { EntrepriseMySuffixDetailComponent } from './entreprise-my-suffix-detail.component';
import { EntrepriseMySuffixPopupComponent } from './entreprise-my-suffix-dialog.component';
import { EntrepriseMySuffixDeletePopupComponent } from './entreprise-my-suffix-delete-dialog.component';

@Injectable()
export class EntrepriseMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const entrepriseRoute: Routes = [
    {
        path: 'entreprise-my-suffix',
        component: EntrepriseMySuffixComponent,
        resolve: {
            'pagingParams': EntrepriseMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entreprise-my-suffix/:id',
        component: EntrepriseMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entreprisePopupRoute: Routes = [
    {
        path: 'entreprise-my-suffix-new',
        component: EntrepriseMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entreprise-my-suffix/:id/edit',
        component: EntrepriseMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entreprise-my-suffix/:id/delete',
        component: EntrepriseMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'michelRecrutement2App.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
