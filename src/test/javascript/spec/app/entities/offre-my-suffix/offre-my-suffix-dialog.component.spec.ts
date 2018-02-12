/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MichelRecrutement2TestModule } from '../../../test.module';
import { OffreMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix-dialog.component';
import { OffreMySuffixService } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.service';
import { OffreMySuffix } from '../../../../../../main/webapp/app/entities/offre-my-suffix/offre-my-suffix.model';
import { EntrepriseMySuffixService } from '../../../../../../main/webapp/app/entities/entreprise-my-suffix';
import { CategorieOffreMySuffixService } from '../../../../../../main/webapp/app/entities/categorie-offre-my-suffix';
import { CompetenceMySuffixService } from '../../../../../../main/webapp/app/entities/competence-my-suffix';

describe('Component Tests', () => {

    describe('OffreMySuffix Management Dialog Component', () => {
        let comp: OffreMySuffixDialogComponent;
        let fixture: ComponentFixture<OffreMySuffixDialogComponent>;
        let service: OffreMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MichelRecrutement2TestModule],
                declarations: [OffreMySuffixDialogComponent],
                providers: [
                    EntrepriseMySuffixService,
                    CategorieOffreMySuffixService,
                    CompetenceMySuffixService,
                    OffreMySuffixService
                ]
            })
            .overrideTemplate(OffreMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OffreMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OffreMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OffreMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.offre = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'offreListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OffreMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.offre = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'offreListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
