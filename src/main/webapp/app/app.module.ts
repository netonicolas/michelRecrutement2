import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { MichelRecrutement2SharedModule, UserRouteAccessService } from './shared';
import { MichelRecrutement2AppRoutingModule} from './app-routing.module';
import { MichelRecrutement2HomeModule } from './home/home.module';
import { MichelRecrutement2AdminModule } from './admin/admin.module';
import { MichelRecrutement2AccountModule } from './account/account.module';
import { MichelRecrutement2EntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        MichelRecrutement2AppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        MichelRecrutement2SharedModule,
        MichelRecrutement2HomeModule,
        MichelRecrutement2AdminModule,
        MichelRecrutement2AccountModule,
        MichelRecrutement2EntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class MichelRecrutement2AppModule {}
