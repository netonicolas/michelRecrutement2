/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { OffreMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix-delete-dialog.component';
import { OffreMySuffixService } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.service';

describe('Component Tests', () => {

    describe('OffreMySuffix Management Delete Component', () => {
        let comp: OffreMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<OffreMySuffixDeleteDialogComponent>;
        let service: OffreMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [OffreMySuffixDeleteDialogComponent],
                providers: [
                    OffreMySuffixService
                ]
            })
            .overrideTemplate(OffreMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OffreMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OffreMySuffixService);
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
