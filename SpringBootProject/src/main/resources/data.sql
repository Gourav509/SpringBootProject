insert into
        oauth_client_details (
        client_id,
        client_secret,
        resource_ids,
        scope,
        authorized_grant_types,
        authorities,
        access_token_validity,
        refresh_token_validity
        )
        values(
        'demo_client',
        '$2a$10$bSTvCHyw8b0N7xZ1CHtBL..WF/YGvatuPiAkzNCr4owaj4VygxBU.',
        'crud',
        'read,write',
        'authorization_code,check_token,refresh_token,password',
        'ROLE_CLIENT',
        2500,
        250000
        );