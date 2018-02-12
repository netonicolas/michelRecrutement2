/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { EntrepriseMySuffixComponent } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix.component';
import { EntrepriseMySuffixService } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix.service';
import { EntrepriseMySuffix } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix.model';

describe('Component Tests', () => {

    describe('EntrepriseMySuffix Management Component', () => {
        let comp: EntrepriseMySuffixComponent;
        let fixture: ComponentFixture<EntrepriseMySuffixComponent>;
        let service: EntrepriseMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [EntrepriseMySuffixComponent],
                providers: [
                    EntrepriseMySuffixService
                ]
            })
            .overrideTemplate(EntrepriseMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntrepriseMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntrepriseMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EntrepriseMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.entreprises[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
