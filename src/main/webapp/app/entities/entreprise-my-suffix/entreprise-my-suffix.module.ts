import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MichelRecrutement2SharedModule } from '../../shared';
import {
    EntrepriseMySuffixService,
    EntrepriseMySuffixPopupService,
    EntrepriseMySuffixComponent,
    EntrepriseMySuffixDetailComponent,
    EntrepriseMySuffixDialogComponent,
    EntrepriseMySuffixPopupComponent,
    EntrepriseMySuffixDeletePopupComponent,
    EntrepriseMySuffixDeleteDialogComponent,
    entrepriseRoute,
    entreprisePopupRoute,
    EntrepriseMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...entrepriseRoute,
    ...entreprisePopupRoute,
];

@NgModule({
    imports: [
        MichelRecrutement2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EntrepriseMySuffixComponent,
        EntrepriseMySuffixDetailComponent,
        EntrepriseMySuffixDialogComponent,
        EntrepriseMySuffixDeleteDialogComponent,
        EntrepriseMySuffixPopupComponent,
        EntrepriseMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EntrepriseMySuffixComponent,
        EntrepriseMySuffixDialogComponent,
        EntrepriseMySuffixPopupComponent,
        EntrepriseMySuffixDeleteDialogComponent,
        EntrepriseMySuffixDeletePopupComponent,
    ],
    providers: [
        EntrepriseMySuffixService,
        EntrepriseMySuffixPopupService,
        EntrepriseMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MichelRecrutement2EntrepriseMySuffixModule {}
