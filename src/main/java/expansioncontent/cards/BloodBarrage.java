package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.BeatOfDeathThatDoesntKillYouPower;
import expansioncontent.powers.VexVinciblePower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class BloodBarrage extends AbstractExpansionCard {
    public final static String ID = makeID("BloodBarrage");

    private static final int MAGIC = 2;

    public BloodBarrage() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_heart.png", "expansioncontentResources/images/1024/bg_boss_heart.png");
        tags.add(expansionContentMod.STUDY);
        this.baseDamage = 3;
        this.exhaust = true;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, "BloodBarrage.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 1; i < this.magicNumber; ++i) {
            this.addToBot(new PummelDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}

