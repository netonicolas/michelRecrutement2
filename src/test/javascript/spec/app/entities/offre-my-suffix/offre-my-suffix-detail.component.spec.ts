/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { OffreMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix-detail.component';
import { OffreMySuffixService } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.service';
import { OffreMySuffix } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.model';

describe('Component Tests', () => {

    describe('OffreMySuffix Management Detail Component', () => {
        let comp: OffreMySuffixDetailComponent;
        let fixture: ComponentFixture<OffreMySuffixDetailComponent>;
        let service: OffreMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [OffreMySuffixDetailComponent],
                providers: [
                    OffreMySuffixService
                ]
            })
            .overrideTemplate(OffreMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OffreMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OffreMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new OffreMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.offre).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
