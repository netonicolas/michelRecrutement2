/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { LieuMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix-dialog.component';
import { LieuMySuffixService } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.service';
import { LieuMySuffix } from '../../../../../../main/webapp/app/entities/lieu-my-suffix/lieu-my-suffix.model';

describe('Component Tests', () => {

    describe('LieuMySuffix Management Dialog Component', () => {
        let comp: LieuMySuffixDialogComponent;
        let fixture: ComponentFixture<LieuMySuffixDialogComponent>;
        let service: LieuMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [LieuMySuffixDialogComponent],
                providers: [
                    LieuMySuffixService
                ]
            })
            .overrideTemplate(LieuMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LieuMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LieuMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LieuMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.lieu = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lieuListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new LieuMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.lieu = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lieuListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
