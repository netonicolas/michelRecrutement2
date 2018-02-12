/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { CompetenceMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix-dialog.component';
import { CompetenceMySuffixService } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.service';
import { CompetenceMySuffix } from '../../../../../../main/webapp/app/entities/competence-my-suffix/competence-my-suffix.model';
import { OffreMySuffixService } from '../../../../../../main/webapp/app/entities/offre-my-suffix';

describe('Component Tests', () => {

    describe('CompetenceMySuffix Management Dialog Component', () => {
        let comp: CompetenceMySuffixDialogComponent;
        let fixture: ComponentFixture<CompetenceMySuffixDialogComponent>;
        let service: CompetenceMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [CompetenceMySuffixDialogComponent],
                providers: [
                    OffreMySuffixService,
                    CompetenceMySuffixService
                ]
            })
            .overrideTemplate(CompetenceMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompetenceMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetenceMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompetenceMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.competence = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'competenceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompetenceMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.competence = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'competenceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
