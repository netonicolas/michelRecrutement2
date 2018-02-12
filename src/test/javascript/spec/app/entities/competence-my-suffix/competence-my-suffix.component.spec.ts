/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CompetenceMySuffixComponent } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.component';
import { CompetenceMySuffixService } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.service';
import { CompetenceMySuffix } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.model';

describe('Component Tests', () => {

    describe('CompetenceMySuffix Management Component', () => {
        let comp: CompetenceMySuffixComponent;
        let fixture: ComponentFixture<CompetenceMySuffixComponent>;
        let service: CompetenceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CompetenceMySuffixComponent],
                providers: [
                    CompetenceMySuffixService
                ]
            })
            .overrideTemplate(CompetenceMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompetenceMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetenceMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompetenceMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.competences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
