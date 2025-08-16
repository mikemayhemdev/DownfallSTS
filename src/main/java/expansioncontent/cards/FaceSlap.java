package expansioncontent.cards;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import expansioncontent.expansionContentMod;

import static champ.ChampMod.vigor;

import java.util.ArrayList;

public class FaceSlap extends AbstractExpansionCard {

    //Super Vicious Mockery
    public final static String ID = makeID("SuperViciousMockery");

    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;
    private static final int downfallMagic = 2;
    private static final int UPGRADE_downfallMagic = 1;

    public FaceSlap() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        //todo skill bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_champ.png", "expansioncontentResources/images/1024/bg_boss_champ.png");

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseDownfallMagic = downfallMagic;
        baseMagicNumber = magicNumber = MAGIC;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "ViciousMockery.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        vigor(magicNumber);
        applyToEnemy(m, autoWeak(m, downfallMagic));
        atb(new SFXAction("VO_CHAMP_2A"));
        atb(new TalkAction(true, getTaunt(), 2.0F, 2.0F));
    }

    private String getTaunt() {
        ArrayList<String> derp = new ArrayList<>();
        derp.add(Champ.DIALOG[0]);
        derp.add(Champ.DIALOG[1]);
        derp.add(Champ.DIALOG[2]);
        derp.add(Champ.DIALOG[3]);
        return derp.get(MathUtils.random(derp.size() - 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDownfall(UPGRADE_downfallMagic);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}

