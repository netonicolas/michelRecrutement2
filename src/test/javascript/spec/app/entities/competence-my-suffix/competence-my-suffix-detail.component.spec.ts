/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CompetenceMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix-detail.component';
import { CompetenceMySuffixService } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.service';
import { CompetenceMySuffix } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.model';

describe('Component Tests', () => {

    describe('CompetenceMySuffix Management Detail Component', () => {
        let comp: CompetenceMySuffixDetailComponent;
        let fixture: ComponentFixture<CompetenceMySuffixDetailComponent>;
        let service: CompetenceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CompetenceMySuffixDetailComponent],
                providers: [
                    CompetenceMySuffixService
                ]
            })
            .overrideTemplate(CompetenceMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompetenceMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetenceMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompetenceMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.competence).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
