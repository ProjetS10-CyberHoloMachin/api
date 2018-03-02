/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MicroTestModule } from '../../../test.module';
import { InfoComponent } from '../../../../../../main/webapp/app/entities/info/info.component';
import { InfoService } from '../../../../../../main/webapp/app/entities/info/info.service';
import { Info } from '../../../../../../main/webapp/app/entities/info/info.model';

describe('Component Tests', () => {

    describe('Info Management Component', () => {
        let comp: InfoComponent;
        let fixture: ComponentFixture<InfoComponent>;
        let service: InfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MicroTestModule],
                declarations: [InfoComponent],
                providers: [
                    InfoService
                ]
            })
            .overrideTemplate(InfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Info(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.infos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
