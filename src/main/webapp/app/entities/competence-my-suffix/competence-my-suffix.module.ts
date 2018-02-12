import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MichelRecrutement2SharedModule } from '../../shared';
import {
    CompetenceMySuffixService,
    CompetenceMySuffixPopupService,
    CompetenceMySuffixComponent,
    CompetenceMySuffixDetailComponent,
    CompetenceMySuffixDialogComponent,
    CompetenceMySuffixPopupComponent,
    CompetenceMySuffixDeletePopupComponent,
    CompetenceMySuffixDeleteDialogComponent,
    competenceRoute,
    competencePopupRoute,
} from './';

const ENTITY_STATES = [
    ...competenceRoute,
    ...competencePopupRoute,
];

@NgModule({
    imports: [
        MichelRecrutement2SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompetenceMySuffixComponent,
        CompetenceMySuffixDetailComponent,
        CompetenceMySuffixDialogComponent,
        CompetenceMySuffixDeleteDialogComponent,
        CompetenceMySuffixPopupComponent,
        CompetenceMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CompetenceMySuffixComponent,
        CompetenceMySuffixDialogComponent,
        CompetenceMySuffixPopupComponent,
        CompetenceMySuffixDeleteDialogComponent,
        CompetenceMySuffixDeletePopupComponent,
    ],
    providers: [
        CompetenceMySuffixService,
        CompetenceMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MichelRecrutement2CompetenceMySuffixModule {}
