package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class LatchOn extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("LatchOn");

    // Card constants
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int COST = 1;

    public LatchOn() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "LatchOn.png");
        this.cardsToPreview = new ViperEssence();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage to all enemies
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead && !monster.isDying) {
                addToBot(new DamageAction(monster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }

        // Add Viper Essence to the player's hand
        AbstractCard viperEssence = new ViperEssence();
        addToBot(new MakeTempCardInHandAction(viperEssence, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
