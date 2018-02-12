/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { OffreMySuffixComponent } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.component';
import { OffreMySuffixService } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.service';
import { OffreMySuffix } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.model';

describe('Component Tests', () => {

    describe('OffreMySuffix Management Component', () => {
        let comp: OffreMySuffixComponent;
        let fixture: ComponentFixture<OffreMySuffixComponent>;
        let service: OffreMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [OffreMySuffixComponent],
                providers: [
                    OffreMySuffixService
                ]
            })
            .overrideTemplate(OffreMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OffreMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OffreMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new OffreMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.offres[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
