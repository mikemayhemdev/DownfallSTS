package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.SlimeboundMod;
import slimebound.actions.RandomStudyCardAction;
import slimebound.cards.AbstractSlimeboundCard;
import slimebound.characters.SlimeboundCharacter;
import slimebound.vfx.TinyHatParticle;

public class SelfDamagePreventRelic extends CustomRelic {
    public static final String ID = "Slimebound:SelfDamagePreventRelic";
    public static final String IMG_PATH = "relics/protectiveGear.png";
    public static final String OUTLINE_IMG_PATH = "relics/protectiveGearOutline.png";

    public SelfDamagePreventRelic() {
        super(ID, new Texture(SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SelfDamagePreventRelic();
    }

}