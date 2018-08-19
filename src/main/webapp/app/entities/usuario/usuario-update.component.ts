import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from './usuario.service';
import { IUser, UserService } from 'app/core';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona';

@Component({
    selector: 'jhi-usuario-update',
    templateUrl: './usuario-update.component.html'
})
export class UsuarioUpdateComponent implements OnInit {
    private _usuario: IUsuario;
    isSaving: boolean;

    users: IUser[];

    personas: IPersona[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private usuarioService: UsuarioService,
        private userService: UserService,
        private personaService: PersonaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ usuario }) => {
            this.usuario = usuario;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.personaService.query({ filter: 'usuario-is-null' }).subscribe(
            (res: HttpResponse<IPersona[]>) => {
                if (!this.usuario.persona || !this.usuario.persona.id) {
                    this.personas = res.body;
                } else {
                    this.personaService.find(this.usuario.persona.id).subscribe(
                        (subRes: HttpResponse<IPersona>) => {
                            this.personas = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.usuario.id !== undefined) {
            this.subscribeToSaveResponse(this.usuarioService.update(this.usuario));
        } else {
            this.subscribeToSaveResponse(this.usuarioService.create(this.usuario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUsuario>>) {
        result.subscribe((res: HttpResponse<IUsuario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackPersonaById(index: number, item: IPersona) {
        return item.id;
    }
    get usuario() {
        return this._usuario;
    }

    set usuario(usuario: IUsuario) {
        this._usuario = usuario;
    }
}
