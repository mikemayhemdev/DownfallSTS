package reskinContent.skinCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;
import reskinContent.helpers.PortraitHexaghostOrb;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import reskinContent.vfx.PortraitScreenOnFireEffect;

import java.util.ArrayList;

public class Hexago extends AbstractSkinCharacter {
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


    public static boolean hexaghostMask_switch = true;


    public Hexago() {
        this.original_IMG = ImageMaster.loadImage("hexamodResources/images/charSelect/charBG.png");
        this.portrait_waifu = ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/portrait_waifu.png"));
        this.portrait_waifu2 = ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/portrait_waifu2.png"));
        this.hexaghostTextureMask = ImageMaster.loadImage(reskinContent.assetPath("img/HexaghostMod/portrait_waifu_m.png"));

        this.ID = CardCrawlGame.languagePack.getCharacterString("hexamod:theHexaghost").NAMES[0];
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinHexaghost").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/HexaghostMod/animation/Hexaghost_portrait");


        orbs.clear();
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
            hexaghostMask_switch = false;
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
        switch (reskinCount) {
            case 0:
                reskinAnimation = false;
                reskinContent.saveSettings();
                return original_IMG;
            case 1:
                reskinAnimation = true;
                reskinContent.saveSettings();
                if (portraitAnimationType == 0) {
                    if (hexaghostMask_switch) {
                        return hexaghostTextureMask;
                    } else {
                        return portrait_waifu;
                    }

                } else {
                    return portrait_waifu2;
                }
            default:
                return original_IMG;
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
    public void clearOrbs() {
        orbs.clear();
    }



    @Override
    public void update() {
        if (reskinCount == 1) {
            if (portraitAnimationType != 0) {
                if (CharacterSelectScreenPatches.hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                    CharacterSelectScreenPatches.hexaghostMask.clicked = false;

                    if (hexaghostMask_switch) {
                        portraitState.setAnimation(3, "Mask_off", false);
                        hexaghostMask_switch = false;
                        reskinContent.hexaghostMask = true;
                        reskinContent.saveSettings();
                    } else {
                        portraitState.setAnimation(3, "Mask_on", false);
                        portraitState.addAnimation(3, "maskHalo_loop", true, 0.0f);
                        hexaghostMask_switch = true;
                        reskinContent.hexaghostMask = false;
                        reskinContent.saveSettings();
                    }
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
                CharacterSelectScreenPatches.hexaghostMask.move(
                        portraitSkeleton.getX() + portraitSkeleton.findBone("Mask").getWorldX(),
                        portraitSkeleton.getY() + portraitSkeleton.findBone("Mask").getWorldY()
                );
            } else {
                if (CharacterSelectScreenPatches.hexaghostMask.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                    CharacterSelectScreenPatches.hexaghostMask.clicked = false;

                    if (hexaghostMask_switch) {
                        hexaghostMask_switch = false;
                        reskinContent.hexaghostMask = true;
                        reskinContent.saveSettings();
                    } else {
                        hexaghostMask_switch = true;
                        reskinContent.hexaghostMask = false;
                        reskinContent.saveSettings();
                    }
                }
            }
        }
    }
}


