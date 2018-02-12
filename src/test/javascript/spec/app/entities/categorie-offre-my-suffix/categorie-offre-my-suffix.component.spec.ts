/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CategorieOffreMySuffixComponent } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.component';
import { CategorieOffreMySuffixService } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.service';
import { CategorieOffreMySuffix } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.model';

describe('Component Tests', () => {

    describe('CategorieOffreMySuffix Management Component', () => {
        let comp: CategorieOffreMySuffixComponent;
        let fixture: ComponentFixture<CategorieOffreMySuffixComponent>;
        let service: CategorieOffreMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CategorieOffreMySuffixComponent],
                providers: [
                    CategorieOffreMySuffixService
                ]
            })
            .overrideTemplate(CategorieOffreMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieOffreMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieOffreMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CategorieOffreMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.categorieOffres[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
