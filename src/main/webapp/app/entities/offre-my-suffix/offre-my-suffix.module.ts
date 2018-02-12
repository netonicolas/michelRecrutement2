import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MichelRecrutement2SharedModule } from '../../shared';
import {
    OffreMySuffixService,
    OffreMySuffixPopupService,
    OffreMySuffixComponent,
    OffreMySuffixDetailComponent,
    OffreMySuffixDialogComponent,
    OffreMySuffixPopupComponent,
    OffreMySuffixDeletePopupComponent,
    OffreMySuffixDeleteDialogComponent,
    offreRoute,
    offrePopupRoute,
} from './';

const ENTITY_STATES = [
    ...offreRoute,
    ...offrePopupRoute,
];

@NgModule({
    imports: [
        MichelRecrutement2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OffreMySuffixComponent,
        OffreMySuffixDetailComponent,
        OffreMySuffixDialogComponent,
        OffreMySuffixDeleteDialogComponent,
        OffreMySuffixPopupComponent,
        OffreMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        OffreMySuffixComponent,
        OffreMySuffixDialogComponent,
        OffreMySuffixPopupComponent,
        OffreMySuffixDeleteDialogComponent,
        OffreMySuffixDeletePopupComponent,
    ],
    providers: [
        OffreMySuffixService,
        OffreMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MichelRecrutement2OffreMySuffixModule {}
