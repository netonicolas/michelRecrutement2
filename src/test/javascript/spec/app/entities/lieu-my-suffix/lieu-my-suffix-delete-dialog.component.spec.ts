/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { LieuMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix-delete-dialog.component';
import { LieuMySuffixService } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.service';

describe('Component Tests', () => {

    describe('LieuMySuffix Management Delete Component', () => {
        let comp: LieuMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<LieuMySuffixDeleteDialogComponent>;
        let service: LieuMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [LieuMySuffixDeleteDialogComponent],
                providers: [
                    LieuMySuffixService
                ]
            })
            .overrideTemplate(LieuMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LieuMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LieuMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
