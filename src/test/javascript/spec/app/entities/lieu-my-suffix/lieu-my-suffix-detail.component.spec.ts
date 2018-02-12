/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { LieuMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix-detail.component';
import { LieuMySuffixService } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.service';
import { LieuMySuffix } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.model';

describe('Component Tests', () => {

    describe('LieuMySuffix Management Detail Component', () => {
        let comp: LieuMySuffixDetailComponent;
        let fixture: ComponentFixture<LieuMySuffixDetailComponent>;
        let service: LieuMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [LieuMySuffixDetailComponent],
                providers: [
                    LieuMySuffixService
                ]
            })
            .overrideTemplate(LieuMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LieuMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LieuMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new LieuMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lieu).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
