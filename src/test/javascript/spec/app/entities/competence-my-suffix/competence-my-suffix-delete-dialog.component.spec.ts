/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CompetenceMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix-delete-dialog.component';
import { CompetenceMySuffixService } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.service';

describe('Component Tests', () => {

    describe('CompetenceMySuffix Management Delete Component', () => {
        let comp: CompetenceMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<CompetenceMySuffixDeleteDialogComponent>;
        let service: CompetenceMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CompetenceMySuffixDeleteDialogComponent],
                providers: [
                    CompetenceMySuffixService
                ]
            })
            .overrideTemplate(CompetenceMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompetenceMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetenceMySuffixService);
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
