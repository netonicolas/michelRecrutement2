import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MichelRecrutement2OffreMySuffixModule } from './offre-my-suffix/offre-my-suffix.module';
import { MichelRecrutement2EntrepriseMySuffixModule } from './entreprise-my-suffix/entreprise-my-suffix.module';
import { MichelRecrutement2LieuMySuffixModule } from './lieu-my-suffix/lieu-my-suffix.module';
import { MichelRecrutement2CategorieOffreMySuffixModule } from './categorie-offre-my-suffix/categorie-offre-my-suffix.module';
import { MichelRecrutement2CompetenceMySuffixModule } from './competence-my-suffix/competence-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MichelRecrutement2OffreMySuffixModule,
        MichelRecrutement2EntrepriseMySuffixModule,
        MichelRecrutement2LieuMySuffixModule,
        MichelRecrutement2CategorieOffreMySuffixModule,
        MichelRecrutement2CompetenceMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MichelRecrutement2EntityModule {}
