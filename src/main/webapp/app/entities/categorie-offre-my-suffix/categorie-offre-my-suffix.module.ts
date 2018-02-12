import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MichelRecrutement2SharedModule } from '../../shared';
import {
    CategorieOffreMySuffixService,
    CategorieOffreMySuffixPopupService,
    CategorieOffreMySuffixComponent,
    CategorieOffreMySuffixDetailComponent,
    CategorieOffreMySuffixDialogComponent,
    CategorieOffreMySuffixPopupComponent,
    CategorieOffreMySuffixDeletePopupComponent,
    CategorieOffreMySuffixDeleteDialogComponent,
    categorieOffreRoute,
    categorieOffrePopupRoute,
} from './';

const ENTITY_STATES = [
    ...categorieOffreRoute,
    ...categorieOffrePopupRoute,
];

@NgModule({
    imports: [
        MichelRecrutement2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CategorieOffreMySuffixComponent,
        CategorieOffreMySuffixDetailComponent,
        CategorieOffreMySuffixDialogComponent,
        CategorieOffreMySuffixDeleteDialogComponent,
        CategorieOffreMySuffixPopupComponent,
        CategorieOffreMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CategorieOffreMySuffixComponent,
        CategorieOffreMySuffixDialogComponent,
        CategorieOffreMySuffixPopupComponent,
        CategorieOffreMySuffixDeleteDialogComponent,
        CategorieOffreMySuffixDeletePopupComponent,
    ],
    providers: [
        CategorieOffreMySuffixService,
        CategorieOffreMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MichelRecrutement2CategorieOffreMySuffixModule {}
