/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CategorieOffreMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix-detail.component';
import { CategorieOffreMySuffixService } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.service';
import { CategorieOffreMySuffix } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix/categorie-offre-my-suffix.model';

describe('Component Tests', () => {

    describe('CategorieOffreMySuffix Management Detail Component', () => {
        let comp: CategorieOffreMySuffixDetailComponent;
        let fixture: ComponentFixture<CategorieOffreMySuffixDetailComponent>;
        let service: CategorieOffreMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CategorieOffreMySuffixDetailComponent],
                providers: [
                    CategorieOffreMySuffixService
                ]
            })
            .overrideTemplate(CategorieOffreMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieOffreMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieOffreMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CategorieOffreMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.categorieOffre).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
