package reskinContent.skinCharacter.skins.Hexaghost;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import reskinContent.helpers.PortraitHexaghostOrb;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;
import reskinContent.vfx.PortraitScreenOnFireEffect;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;

public class Hexago extends AbstractSkin {
    public Texture hexaghostTextureMask;

    public static ArrayList<PortraitHexaghostOrb> orbs = new ArrayList();
    private static final String ACTIVATE_STATE = "Activate";
    private static final String ACTIVATE_ORB = "Activate Orb";
    private static final String DEACTIVATE_ALL_ORBS = "Deactivate";
    private static int orbActiveCount = 0;

    private static final Float ghostFireTimer_time = 1.0f;
    private static final Float giantGhostFireTimer_time = 3.0f;
    private static final Float ghostFireDeactivate_time = 0.5f;
    private static float ghostFireTimer = ghostFireTimer_time;
    private static boolean giantGhostFire = false;
    private static float giantGhostFireTimer = giantGhostFireTimer_time;


    public Hitbox hexaghostMaskHitBox;
    public boolean maskClicked = false;

    public Hexago() {
        this.portraitStatic_IMG = ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/Hexago/portrait_waifu.png"));
        this.portraitAnimation_IMG = ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/Hexago/portrait_waifu2.png"));
        this.hexaghostTextureMask = ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/Hexago/portrait_waifu_m.png"));

        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinHexaghost").TEXT[0];
        this.DESCRIPTION = CardCrawlGame.languagePack.getCharacterString(TheHexaghost.ID).TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/HexaghostMod/Hexago/animation/Hexaghost_portrait");

        this.SHOULDER1 =  "reskinContent/img/HexaghostMod/Hexago/shoulder2.png";
        this.SHOULDER2 = "reskinContent/img/HexaghostMod/Hexago/shoulder.png";
        this.CORPSE = "hexamodResources/images/char/mainChar/corpse.png";
        this.atlasURL = "reskinContent/img/HexaghostMod/Hexago/animation/Hexaghost_self_downfall.atlas";
        this.jsonURL = "reskinContent/img/HexaghostMod/Hexago/animation/Hexaghost_self_downfall.json";
        this.renderscale = 1.0F;

        hexaghostMaskHitBox = new Hitbox(250.0f * Settings.scale, 350.0f * Settings.scale);
        hexaghostMaskHitBox.move(1350.0F * Settings.scale, 780.0F * Settings.scale);

        orbs.clear();
    }

    @Override
    public String getSHOULDER1() {
        if(reskinContent.hexaghostMask){
            return "reskinContent/img/HexaghostMod/Hexago/shoulder2.png";
        }else {
            return "reskinContent/img/HexaghostMod/Hexago/shoulderMask2.png";
        }
    }

    @Override
    public String getSHOULDER2() {
        if(reskinContent.hexaghostMask){
            return "reskinContent/img/HexaghostMod/Hexago/shoulder.png";
        }else {
            return "reskinContent/img/HexaghostMod/Hexago/shoulderMask.png";
        }
    }

    @Override
    public void loadPortraitAnimation() {
        super.loadPortraitAnimation();

        if (orbs.size() == 0) {
            createOrbs();
            for (PortraitHexaghostOrb orb : orbs) {
                orb.deactivate();
            }
            hexaghostChangeState(ACTIVATE_STATE);
        }
    }

    @Override
    public void setAnimation() {
        portraitState.setAnimation(1, "fade_in", false);
        portraitState.addAnimation(0, "idle_loop_Mask", true, 0.0f);
        portraitState.addAnimation(2, "PlasmaRation", true, 0.0f);
        portraitState.addAnimation(3, "maskHalo_fade_in", true, 0.0f);
        portraitState.addAnimation(3, "maskHalo_loop", true, 0.0f);


        if (reskinContent.hexaghostMask) {
            portraitState.addAnimation(3, "Mask_off", false, 1.0f);
        }

    }

    @Override
    public void InitializeStaticPortraitVar() {
        giantGhostFire = false;
        ghostFireTimer = ghostFireTimer_time;
        giantGhostFireTimer = giantGhostFireTimer_time;
        orbActiveCount = 0;
    }

    @Override
    public Texture updateBgImg() {
        reskinContent.saveSettings();
        if (portraitAnimationType == 0) {
            if (!reskinContent.hexaghostMask) {
                return hexaghostTextureMask;
            } else {
                return portraitStatic_IMG;
            }
        } else {
            return portraitAnimation_IMG;
        }
    }


    private static void createOrbs() {
        orbs.add(new PortraitHexaghostOrb(-90.0F, 380.0F, orbs.size()));
        orbs.add(new PortraitHexaghostOrb(90.0F, 380.0F, orbs.size()));
        orbs.add(new PortraitHexaghostOrb(160.0F, 250.0F, orbs.size()));
        orbs.add(new PortraitHexaghostOrb(90.0F, 120.0F, orbs.size()));
        orbs.add(new PortraitHexaghostOrb(-90.0F, 120.0F, orbs.size()));
        orbs.add(new PortraitHexaghostOrb(-160.0F, 250.0F, orbs.size()));
    }


    public void hexaghostChangeState(String stateName) {
        switch (stateName) {
            case ACTIVATE_STATE:
                for (PortraitHexaghostOrb orb : orbs) {
                    orb.activate(this.portraitSkeleton.getX(), this.portraitSkeleton.getY());
                }
                orbActiveCount = 6;
                break;

            case ACTIVATE_ORB:
                for (PortraitHexaghostOrb orb : orbs) {
                    if (!orb.activated) {
                        orb.activate(this.portraitSkeleton.getX(), this.portraitSkeleton.getY());
                        break;
                    }
                }

                orbActiveCount++;

                if (orbActiveCount > 6) {
                    for (PortraitHexaghostOrb orb : orbs) {
                        orb.hellFlameActivate();
                    }

                    CharacterSelectScreenPatches.char_effectsQueue.add(new PortraitScreenOnFireEffect());
                    orbActiveCount = 0;
                    giantGhostFire = true;
                    ghostFireTimer = ghostFireTimer_time + ghostFireDeactivate_time;
                } else {
                    ghostFireTimer = ghostFireTimer_time;
                }
                break;


            case DEACTIVATE_ALL_ORBS:
                for (PortraitHexaghostOrb orb : orbs) {
                    orb.deactivate();
                }
                CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
                CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

                giantGhostFire = false;
                giantGhostFireTimer = giantGhostFireTimer_time;
                break;
        }
    }


    @Override
    public void setPos() {
        portraitSkeleton.setPosition(1266.0f * Settings.scale, Settings.HEIGHT - 597.0f * Settings.scale);
    }

    @Override
    public void clearWhenClick() {
        orbs.clear();
    }


    @Override
    public void update() {
        if (InputHelper.justClickedLeft && hexaghostMaskHitBox.hovered)
            hexaghostMaskHitBox.clickStarted = true;

        hexaghostMaskHitBox.update();

        if (portraitAnimationType > 0) {
            if (hexaghostMaskHitBox.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                hexaghostMaskHitBox.clicked = false;


                if (!reskinContent.hexaghostMask) {
                    portraitState.setAnimation(3, "Mask_off", false);
                } else {
                    portraitState.setAnimation(3, "Mask_on", false);
                    portraitState.addAnimation(3, "maskHalo_loop", true, 0.0f);
                }

                reskinContent.hexaghostMask = !reskinContent.hexaghostMask;
                reskinContent.saveSettings();
            }

//  地狱火相关
            if (orbs.size() != 0) {
                if (!giantGhostFire) {
                    ghostFireTimer -= Gdx.graphics.getDeltaTime();
                    if (ghostFireTimer < 0) {
                        hexaghostChangeState(ACTIVATE_ORB);
                    }
                } else {
                    giantGhostFireTimer -= Gdx.graphics.getDeltaTime();
                    if (giantGhostFireTimer <= 0) {
                        hexaghostChangeState(DEACTIVATE_ALL_ORBS);
                    }
                }
            }

// 鬼火更新
            for (PortraitHexaghostOrb orb : orbs) {
                orb.update(portraitSkeleton.getX(), portraitSkeleton.getY());
            }
//面具hitbox
            hexaghostMaskHitBox.move(
                    portraitSkeleton.getX() + portraitSkeleton.findBone("Mask").getWorldX(),
                    portraitSkeleton.getY() + portraitSkeleton.findBone("Mask").getWorldY()
            );
        } else {
            if (hexaghostMaskHitBox.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                hexaghostMaskHitBox.clicked = false;
                maskClicked = true;
                reskinContent.hexaghostMask = !reskinContent.hexaghostMask;
                reskinContent.saveSettings();
            }
        }

    }


    @Override
    public void extraHitboxRender(SpriteBatch sb) {
        hexaghostMaskHitBox.render(sb);
    }

    @Override
    public Boolean extraHitboxClickCheck() {
        if (maskClicked) {
            maskClicked = false;
            return true;
        } else {
            return false;
        }
    }
}


