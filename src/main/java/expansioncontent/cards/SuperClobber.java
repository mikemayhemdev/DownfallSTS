package expansioncontent.cards;


import champ.ChampMod;
import champ.relics.PowerArmor;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class SuperClobber extends AbstractExpansionCard {

    //Super Clobber
    public final static String ID = makeID("SuperClobber");

    private static final int DAMAGE = 6;

    public SuperClobber() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        //todo attack bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_champ.png", "expansioncontentResources/images/1024/bg_boss_champ.png");

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        expansionContentMod.loadJokeCardImage((AbstractCard)this, "SuperClobber.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new WallopAction(m, makeInfo()));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

}
