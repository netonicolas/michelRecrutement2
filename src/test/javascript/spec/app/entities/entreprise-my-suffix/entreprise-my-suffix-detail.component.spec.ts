/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { EntrepriseMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix-detail.component';
import { EntrepriseMySuffixService } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix.service';
import { EntrepriseMySuffix } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix.model';

describe('Component Tests', () => {

    describe('EntrepriseMySuffix Management Detail Component', () => {
        let comp: EntrepriseMySuffixDetailComponent;
        let fixture: ComponentFixture<EntrepriseMySuffixDetailComponent>;
        let service: EntrepriseMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [EntrepriseMySuffixDetailComponent],
                providers: [
                    EntrepriseMySuffixService
                ]
            })
            .overrideTemplate(EntrepriseMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntrepriseMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntrepriseMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EntrepriseMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.entreprise).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
