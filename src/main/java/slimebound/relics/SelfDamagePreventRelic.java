package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.SlimeboundMod;
import sun.security.krb5.internal.crypto.Des;

public class SelfDamagePreventRelic extends CustomRelic {
    public static final String ID = "Slimebound:SelfDamagePreventRelic";
    public static final String IMG_PATH = "relics/protectiveGear.png";
    public static final String OUTLINE_IMG_PATH = "relics/protectiveGearOutline.png";

    //Protective Gear

    //Variables
    public static final int AMOUNT = 3;

    public SelfDamagePreventRelic() {
        super(ID, new Texture(SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SelfDamagePreventRelic();
    }

}