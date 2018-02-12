import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MichelRecrutement2SharedModule } from '../../shared';
import {
    LieuMySuffixService,
    LieuMySuffixPopupService,
    LieuMySuffixComponent,
    LieuMySuffixDetailComponent,
    LieuMySuffixDialogComponent,
    LieuMySuffixPopupComponent,
    LieuMySuffixDeletePopupComponent,
    LieuMySuffixDeleteDialogComponent,
    lieuRoute,
    lieuPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lieuRoute,
    ...lieuPopupRoute,
];

@NgModule({
    imports: [
        MichelRecrutement2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LieuMySuffixComponent,
        LieuMySuffixDetailComponent,
        LieuMySuffixDialogComponent,
        LieuMySuffixDeleteDialogComponent,
        LieuMySuffixPopupComponent,
        LieuMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LieuMySuffixComponent,
        LieuMySuffixDialogComponent,
        LieuMySuffixPopupComponent,
        LieuMySuffixDeleteDialogComponent,
        LieuMySuffixDeletePopupComponent,
    ],
    providers: [
        LieuMySuffixService,
        LieuMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MichelRecrutement2LieuMySuffixModule {}
