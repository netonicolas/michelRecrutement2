/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { LieuMySuffixComponent } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.component';
import { LieuMySuffixService } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.service';
import { LieuMySuffix } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.model';

describe('Component Tests', () => {

    describe('LieuMySuffix Management Component', () => {
        let comp: LieuMySuffixComponent;
        let fixture: ComponentFixture<LieuMySuffixComponent>;
        let service: LieuMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [LieuMySuffixComponent],
                providers: [
                    LieuMySuffixService
                ]
            })
            .overrideTemplate(LieuMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LieuMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LieuMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new LieuMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lieus[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
