package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedIronWave;
import automaton.cards.encodedcards.EncodedRitualDagger;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.actions.TackleSelfDamageAction;
import slimebound.powers.PreventTackleDamagePower;

public class ChosenStrike extends AbstractBronzeCard {

    public final static String ID = makeID("ChosenStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 10;

    public int baseSelfDamage;
    public int selfDamage;

    public ChosenStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;

        tags.add(AutomatonMod.ENCODES);
        cardsToPreview = new EncodedRitualDagger();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (!AbstractDungeon.player.hasPower(PreventTackleDamagePower.POWER_ID))
            AbstractDungeon.actionManager.addToBottom(new TackleSelfDamageAction(new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
        if (upgraded) addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}