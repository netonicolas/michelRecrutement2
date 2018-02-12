/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { EntrepriseMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix-delete-dialog.component';
import { EntrepriseMySuffixService } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix/entreprise-my-suffix.service';

describe('Component Tests', () => {

    describe('EntrepriseMySuffix Management Delete Component', () => {
        let comp: EntrepriseMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<EntrepriseMySuffixDeleteDialogComponent>;
        let service: EntrepriseMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [EntrepriseMySuffixDeleteDialogComponent],
                providers: [
                    EntrepriseMySuffixService
                ]
            })
            .overrideTemplate(EntrepriseMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntrepriseMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntrepriseMySuffixService);
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
